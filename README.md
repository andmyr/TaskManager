# TaskManager
Условие задачи

Есть исходный файл data.json в assets:
```
{
	"data":{
		"mechanizms":[
			{
				"name":"cars",
				"items":[{"name":"Mersedes"},{"name":"Opel"},{"name":"Clinton"},{"name":"Ferrari"},{"name":"Mazda"},{"name":"Mitsubishi"},{"name":"BMW"},{"name":"Audi"},{"name":"Hammer"}]
			},
			{
				"name":"planes",
				"items":[{"name":"Boeing"},{"name":"Airbus"},{"name":"Falcon"},{"name":"Embraer"},{"name":"Bombardier"},{"name":"Gulfstream"},{"name":"Aerospace"}]
			},
			{
				"name":"ships",
				"items":[{"name":"Imabary"},{"name":"Waigaoqiao"},{"name":"Mipo"},{"name":"Samsung"},{"name":"Tsuneishi"},{"name":"Oshima"},{"name":"Daewoo"}]
			}
		]
	}
}
```
Создать приложение работающее в portrait и landscape orientation, сортирующее названия механизмов в Service, полученные из файла data.json находящимся в assets.
Реализовать сортировку названий механизмов в менеджере задач(TaskManager) который получает список задач(List<Task>) на сортировку и выполняет каждую задачу в отдельном потоке.
Менеджер может выполняет только 2 потока одновременно. Последующие 2 потока менеджер запускает когда выполняться предыдущие 2 (макс. количество одновременно запущенных потоков 2).

Задачи должны выполнять одну из сортировок(extends BaseSort) коллекций механизмов(List<T extends Mechanism>) в алфавитном порядке.
Реализовать классы сортировок BubbleSort, InsertionSort и QuickSort унаследованные от абстрактного класса BaseSort с абстрактным методом sort().
Реализовать сущности Car, Plane и Ship унаследованные от базового абстрактного класса Meshanizm с абстрактным методом getName().
Реализовать фабрику по созданию механизмов.
Реализовать на дженериках. Не использовать сторонние библиотеки.

Пример: 
```
public class SelectionSort<T extends Mechanizm> extends BaseSort<T> {

    @Override
    public void sort(List<T> list) {
		// Выполняется сортировка механизма в алфавитном порядке
	}

}

public class Car extends Mechanizm {

	@Override
	public String getName() {
		// Вернуть название механизма
	}
}
```
Отображать исходные данные при помощи RecyclerView.
При нажатии на кнопку SORT наименования должны начать сортировку и обновлятся по мере их выполнения.
При нажатии на кнопку RESET данные должны по новой сгенерироваться.


## Замечения к текущей реализации

1. Убрать SectionedRecyclerViewAdapter. Использовать только стандартную реализацию RecyclerView.
2. Работа с потоками в TaskManager выполнить используя ExecutorService и Threads.
3. TaskManager работает как сервис в одном потоке, а не как отдельный менеджер контролирующий потоки.
4. Каждый раз при повороте экрана данные заново подгружаются из файла.
5. Нажав Reset, а затем снова Sort программа перестала выполнять сортировку.
