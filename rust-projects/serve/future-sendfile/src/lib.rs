#![crate_name="future_sendfile"]

extern crate futures;
extern crate futures_io;
extern crate libc;

use futures::{Future, Task, Poll};
use futures_io::{Ready, ReadTask, WriteTask};

#[cfg(target_os = "macos")]
#[path="sendfile_macos.rs"]
pub mod sendfile;
