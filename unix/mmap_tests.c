#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

struct mmap_info {
    void        *addr;
    size_t      length;
    int         fd;
};

struct mmap_info* create_tmp_mmap(const size_t length) {
    char    filename[] = "test-tmp-file-XXXXXX";
    int     fd;
    void    *addr;
    struct mmap_info   *info;

    info = (struct mmap_info*) malloc(sizeof(struct mmap_info));
    if (info == NULL) {
        perror("Error allocating memory");
        return NULL;
    }

    fd = mkstemp(filename);

    if (fd < 0) {
        perror("Error creating temp file");
        return NULL;
    }

    printf("Tempfile created: %s\n", filename);

    lseek(fd, length-1, SEEK_SET);
    write(fd, "\0", 1);

    addr = mmap(NULL, length, PROT_WRITE | PROT_READ, MAP_SHARED, fd, 0);

    if (addr == 0) {
        perror("Error creating file mapping");
        return NULL;
    }

    printf("Memory map created. Starting addr: %p, length: %d\n", addr, length);

    info->addr = addr;
    info->fd = fd;
    info->length = length;

    return info;
}


int free_mmap(struct mmap_info *info) {
    int rc = 0;
    if(munmap(info->addr, info->length) == -1) {
        perror("Error freeing memory map info");
        rc = -1;
    }
    close(info->fd);
    free(info);
    return rc;
}


void copy_data(const int srcfd, void* addr, const size_t length) {
    size_t      sz_read = 0, sz_write = 0;
    char        buffer[2048];
    char        *offset_addr = (char*) addr;

    sz_read = read(srcfd, buffer, 2048);

    while(sz_read > 0 && sz_write <= length) {
        printf("Writing %d bytes to memory map. Writen %d bytes already.\n", sz_read, sz_write);
        memcpy(offset_addr, buffer, sz_read);
        sz_write += sz_read;
        offset_addr += sz_read;

        sz_read = read(srcfd, buffer, 2048);
    }

    if (sz_read > 0) {
        fprintf(stderr, "ERROR: memory map overflow\n");
    }

    printf("Finished writing. %d bytes writen.\n", sz_write);
}


int main(int argc, const char *argv[]) {
    int     srcfd;
    size_t  sz_mmap;
    struct mmap_info    *info;

    if (argc < 2) {
        printf("Usage: %s file_name\n", argv[0]);
        return 1;
    }

    srcfd = open(argv[1], O_RDONLY, 0);
    if (srcfd < 0) {
        perror("Error opening file");
        return 1;
    }

    sz_mmap = 10240000;
    info = create_tmp_mmap(sz_mmap);
    if (info == NULL) {
        printf("Error detected when creating memory map\n");
        return -1;
    }
    copy_data(srcfd, info->addr, info->length);
    close(srcfd);

    free_mmap(info);
    printf("All done.\n");
    return 0;
}
