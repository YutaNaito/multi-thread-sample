import threading
from time import sleep

countNum: int = 0
lock1 = threading.Lock()
lock2 = threading.Lock()

def main() -> None:

	th1 = threading.Thread(target=print_hello1)
	th2 = threading.Thread(target=print_hello2)

	th1.start()
	th2.start()

	th1.join()
	th2.join()

	print("finish")

def print_hello1() -> None:
	print("start th1")
	with lock1:
		sleep(0.005)
		with lock2:
			sleep(0.005)
			print("hello th1")


def print_hello2() -> None:
	print("start th2")
	with lock2:
		sleep(0.005)
		with lock1:
			sleep(0.005)
			print("hello th2")


if __name__ == "__main__":
	main()