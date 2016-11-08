# ThePaymentSystemOfHighways

			The Payment System Of Highways

		Коротко о проекте:

	Система оплаты за проезд по платным дорогам.

	Существует центральный сервер, который получает информацию от пунктов пропуска на автобане. В задачу сервера входит калькуляция стоимости проезда в момент получения информации о том что клиент покинул платную зону.

	Автобан включает в себя 10 пунктов пропуска (граф 10 вершин 10 ребер, каждое из ребер имеет свой вес - стоимость). Предполагается, что клиенты зарегистрированы в платежной системе и имеют уникальный номер (т.е. Этот номер

	содержится в сообщении от пункта пропуска), а так же email.

	После расчета  стоимости проезда, система отправляет письмо клиенту с информацией о сумме платежа и  следования, время въезда и выезда.

	Коммуникация через сокеты

	Хранение информации: mongodb

	Рассылка сообщений: GMail

			Работа с проектом:

		Для начала работы надо сначала запустить в папке MongoBD -> out -> artifacts -> MongoBD_jar -> MongoBD.jar

	Данная команда создаст на локальной машине базу данных. БД будет иметь вид:

	checkpoints
	clients
	system.indexes
	{ "_id" : { "$oid" : "580e164959cedd733f6c321d"} , "clientNumber" : "01" , "clientName" : "Bob" , "clientMail" : "bea@sgsoft.com.ua"}
	{ "_id" : { "$oid" : "580e16aa59cedd733f6c321e"} , "clientNumber" : "02" , "clientName" : "Candolina" , "clientMail" : "bea@sgsoft.com.ua"}
	{ "_id" : { "$oid" : "580e16e659cedd733f6c321f"} , "clientNumber" : "03" , "clientName" : "Tom" , "clientMail" : "bea@sgsoft.com.ua"}
	{ "_id" : { "$oid" : "580e18c059cedd733f6c3220"} , "clientNumber" : "04" , "clientName" : "Marta" , "clientMail" : "bea@sgsoft.com.ua"}
	{ "_id" : { "$oid" : "580e192e59cedd733f6c3221"} , "clientNumber" : "05" , "clientName" : "Bender" , "clientMail" : "bea@sgsoft.com.ua"}
	{ "_id" : { "$oid" : "580e1aea59cedd733f6c3222"} , "clientNumber" : "06" , "clientName" : "Jon" , "clientMail" : "bea@sgsoft.com.ua"}
	{ "_id" : { "$oid" : "580e1b1159cedd733f6c3223"} , "clientNumber" : "07" , "clientName" : "Fray" , "clientMail" : "bea@sgsoft.com.ua"}

	...

	{ "_id" : { "$oid" : "580e1cc359cedd733f6c3224"} , "check-in" : "01" , "check-out-01" : "10" , "check-out-02" : "11" , "check-out-03" : "12" , "check-out-04" : "13" , "check-out-05" : "14" , "check-out-06" : "15" , "check-out-07" : "16" , "check-out-08" : "17" , "check-out-09" : "18" , "check-out-10" : "19"}
	{ "_id" : { "$oid" : "580e1ece59cedd733f6c3225"} , "check-in" : "02" , "check-out-01" : "11" , "check-out-02" : "10" , "check-out-03" : "11" , "check-out-04" : "12" , "check-out-05" : "13" , "check-out-06" : "14" , "check-out-07" : "15" , "check-out-08" : "16" , "check-out-09" : "17" , "check-out-10" : "18"}

	...

	Done

		В личном кабинете gmail разрешить доступ непроверенным приложением к почтовому ящику.

	Потом необходимо запустить сервер в папке ThePaymentSystemOfHighways -> out -> artifacts -> ThePaymentSystemOfHighways_jar -> ThePaymentSystemOfHighways.jar

	- Enter your Google mail: <Your Google mail:> - та самая почта к которой был разрешен доступ непроверенным приложениям

	- Enter the password of your Google mail: <password> - пароль от почты	

	- Enter the recipient's address: <recipient's address> - адрес почты на которую будут приходить уведомления

		Далее сервер будет ждать соединения с периферийными устройствами:

	To expect the connection of 10 remote devices
	Port number - 8080

		Запускаем периферийные устройства (запускать надо на той же машине что и сервер или в ): в папке ClientForPaymentSystemOfHighways -> out -> artifacts -> ClientForPaymentSystemOfHighways_jar -> ClientForPaymentSystemOfHighways.jar

	Появляется сообщение: Enter the terminal number (the numbers from 1 to 10): вводимые числа не должны повторяться и выходить за предел.

	Следующее сообщение: Enter the ID of the new client (the numbers from 1 to 10): любое число в заданном диапазоне. Если число ранее вводилось, то будет отправлено письмо с ценой проезда.

	Запускаем следующее периферийное устройство - ClientForPaymentSystemOfHighways -> out -> artifacts -> ClientForPaymentSystemOfHighways_jar -> ClientForPaymentSystemOfHighways.jar

	Можно запустить от 1 до 10 периферийных устройств. 

	При повторном вводе номера клиента - рассчитывается цена поездки и отправляется сообщение на почту.
