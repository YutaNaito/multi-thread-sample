use std::{
    sync::{Arc, Mutex},
    thread,
    time::Duration,
};

fn main() {
    let object1 = Arc::new(Mutex::new(0));
    let object2 = Arc::new(Mutex::new(0));

    let object1_th1 = Arc::clone(&object1);
    let object2_th1 = Arc::clone(&object2);
    let object1_th2 = Arc::clone(&object2);
    let object2_th2 = Arc::clone(&object1);

    let handle1 = thread::spawn(move || {
        println!("start th1");
        let obj1 = object1_th1.lock().unwrap();
        thread::sleep(Duration::from_millis(5));
        let obj2 = object2_th1.lock().unwrap();
        thread::sleep(Duration::from_millis(5));
        println!("obj1:{}, obj2: {}", *obj1, obj2);
    });

    let handle2 = thread::spawn(move || {
        println!("start th2");
        let obj1 = object1_th2.lock().unwrap();
        thread::sleep(Duration::from_millis(5));
        let obj2 = object2_th2.lock().unwrap();
        thread::sleep(Duration::from_millis(5));
        println!("obj1:{}, obj2: {}", *obj1, obj2);
    });

    handle1.join().unwrap();
    handle2.join().unwrap();

    println!("finish");
}
