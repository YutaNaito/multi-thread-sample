### prepare
```shell
sudo apt-get install libboost-all-dev
```

### Build
```cpp
g++ c++/UnThreadSafe.cpp -lboost_thread -lpthread
```