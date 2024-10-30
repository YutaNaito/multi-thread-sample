#include <iostream>
#include <mutex>
#include <thread>

std::mutex mtx1;
std::mutex mtx2;

void print_hello1() {
  std::cout << "start th1\n";
  std::lock_guard<std::mutex> lock1(mtx1);
  std::this_thread::sleep_for(std::chrono::milliseconds(5));
  std::lock_guard<std::mutex> lock2(mtx2);

  std::cout << "hello th1\n";
}

void print_hello2() {
  std::cout << "start th2\n";
  std::lock_guard<std::mutex> lock1(mtx2);
  std::this_thread::sleep_for(std::chrono::milliseconds(5));
  std::lock_guard<std::mutex> lock2(mtx1);

  std::cout << "hello th2\n";
}

int main(void) {
  std::thread th1([&] { print_hello1(); });
  std::thread th2([&] { print_hello2(); });

  th1.join();
  th2.join();

  std::cout << "finish" << std::endl;
}