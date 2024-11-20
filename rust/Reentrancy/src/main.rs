use std::sync::{Arc, Mutex};

// 以下は再入不可なロックでデッドロックが発生するパターン
fn recursive_func_with_non_reentrant(lock: Arc<Mutex<u32>>, count: u32) {
    println!("{}", count);
    if count == 0 {
        return;
    }

    let mut data = lock.lock().unwrap();
    *data += 1;

    recursive_func_with_non_reentrant(lock.clone(), count - 1)
}

fn main() {
    let lock = Arc::new(Mutex::new(0));
    recursive_func_with_non_reentrant(lock, 5);
}
