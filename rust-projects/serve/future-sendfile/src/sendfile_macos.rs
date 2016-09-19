use std::io::{Read, Write, Error, Result};
use std::os::unix::io::AsRawFd;
use std::ptr;
use libc::{sendfile as __sendfile, sf_hdtr, __error};

pub fn sendfile<R, W>(input: &R, output: &W, offset: i64, count: i64) -> Result<i64>
    where R: Read + AsRawFd,
          W: Write + AsRawFd {
    let fd = input.as_raw_fd();
    let s = output.as_raw_fd();
    let mut len = count;
    let p: *mut sf_hdtr = ptr::null_mut();
    println!("Sending {} bytes from fd:{} to fd:{} starting from {}", len, fd, s, offset);
    let ret = unsafe { __sendfile(fd, s, offset, &mut len, p, 0) };
    println!("Sent {} bytes.", len);

    if ret == 0 {
        Ok(len)
    } else {
        let errno = unsafe { *__error() };
        Err(Error::from_raw_os_error(errno))
    }
}
