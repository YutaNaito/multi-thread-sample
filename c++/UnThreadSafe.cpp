#include <boost/bind.hpp>
#include <boost/thread.hpp>
#include <iostream>

void increment(int *x) {
  for (int i = 0; i < 1000; i++) {
    (*x)++;
    boost::this_thread::sleep(boost::posix_time::milliseconds(1));
  }
}

int main(void) {
  int countNum = 0;

  boost::thread th1(boost::bind(increment, &countNum));
  boost::thread th2(boost::bind(increment, &countNum));
  boost::thread th3(boost::bind(increment, &countNum));
  boost::thread th4(boost::bind(increment, &countNum));

  th1.join();
  th2.join();
  th3.join();
  th4.join();

  std::cout << countNum << std::endl;
}