extern crate future_sendfile;

use future_sendfile::sendfile::*;
use std::env::args;
use std::fs::File;
use std::io::{Read, Write};
use std::net::TcpListener;
use std::os::unix::io::AsRawFd;
use std::thread;

fn main() {
    let listener = TcpListener::bind("127.0.0.1:9123").unwrap();
    println!("listening started, ready to accept");
    for stream in listener.incoming() {
        thread::spawn(|| {
            let filename = args().nth(1).unwrap();
            let stream = stream.unwrap();
            let f = File::open(filename).unwrap();
            let size = f.metadata().unwrap().len() as i64;
            println!("Actual file size: {}", size);
            match sendfile(&f, &stream, 0, size) {
                Ok(n) => println!("File sent successfully. Bytes sent: {}", n),
                Err(err) => println!("Error sending file: {}", err)
            }
        });
    }
}
