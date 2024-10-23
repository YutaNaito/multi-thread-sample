import threading
from time import sleep

countNum: int = 0

def main() -> None:

	th1 = threading.Thread(target=incrementNum)
	th2 = threading.Thread(target=incrementNum)
	th3 = threading.Thread(target=incrementNum)
	th4 = threading.Thread(target=incrementNum)
	th5 = threading.Thread(target=incrementNum)

	th1.start()
	th2.start()
	th3.start()
	th4.start()
	th5.start()

	th1.join()
	th2.join()
	th3.join()
	th4.join()
	th5.join()

	print(f"sum is {countNum}")


def incrementNum() -> None:
	global countNum
	for _ in range(200):
		countNum += 1
		sleep(0.003)


if __name__ == "__main__":
	main()