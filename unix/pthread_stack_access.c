#include <stdio.h>
#include <pthread.h>

void* thread_proc(void* arg) {
    int oldVal = *(int*) arg;
    printf("[In thread 1]Old Value: %d\n", oldVal);
    *(int*)arg = 99;
    printf("[In thread 1]New Value: %d\n", *(int*)arg);
}


int main(int argc, const char *argv[])
{
    int var = 18;
    pthread_t tid;
    printf("[In main thread]Old Value: %d\n", var);

    /*
     * &var: this is a pointer to a local variable of the main thread's.
     * Actually the result proves that the other thread COULD access this 
     * thread's stack (pretty dangerous, huh?)
     */
    if (pthread_create(&tid, NULL, thread_proc, &var)) {
        perror("Failed to create thread\n");
        return 1;
    }
    pthread_join(tid, NULL);
    printf("[In main thread, after thread 1 finished]New Value: %d\n", var);
    return 0;
}
