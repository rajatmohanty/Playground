def rearrange(array):
	if len(array) == 0:
		return
	max_num = array[0]
	min_num = array[0]
	max_idx = 0
	min_idx = 0
	for i in range(1, len(array)):
		if array[i] > max_num:
			max_num = array[i]
			max_idx = i
		if array[i] < min_num:
			min_num = array[i]
			min_idx = i
	del array[max_idx]
	del array[min_idx]
	return [max_num] + array + [min_num]

def firstdigit(number):
	while number > 10:
		number = (number - number % 10) / 10
	return int(number)

if __name__ == '__main__':
	print(rearrange([-1,3,5,2,-5,1,99,13]))
	print(firstdigit(87234582794))
