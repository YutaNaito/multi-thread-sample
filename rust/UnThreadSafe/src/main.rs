use std::thread;

fn main() {
    let mut count_num = 0;
    let count_ptr: *mut i32 = &mut count_num;

    let handle1 = thread::spawn(move || unsafe {
        for _ in 0..200 {
            *count_ptr += 1;
        }
    });

    let handle2 = thread::spawn(move || unsafe {
        for _ in 0..200 {
            *count_ptr += 1;
        }
    });

    handle1.join().unwrap();
    handle2.join().unwrap();

    println!("count is {}", unsafe { *count_ptr });
}
