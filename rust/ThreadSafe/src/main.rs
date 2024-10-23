use std::{
    sync::{Arc, Mutex},
    thread,
};

fn main() {
    let count_num = Arc::new(Mutex::new(0));

    let count_num1 = Arc::clone(&count_num);
    let handle1 = thread::spawn(move || {
        for _ in 0..200 {
            let mut count_num = count_num1.lock().unwrap();
            *count_num += 1;
        }
    });

    let count_num2 = Arc::clone(&count_num);
    let handle2 = thread::spawn(move || {
        for _ in 0..200 {
            let mut count_num = count_num2.lock().unwrap();
            *count_num += 1;
        }
    });

    handle1.join().unwrap();
    handle2.join().unwrap();

    println!("count is {}", *count_num.lock().unwrap());
}
