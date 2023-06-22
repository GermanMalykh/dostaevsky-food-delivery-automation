# Демо проект для автоматизации сервиса доставки еды [DOSTAЕВСКИЙ](https://dostaevsky.ru/)

![logo.png](images/icons/logo.png)

> Dostaevsky (Достоевский) — популярная служба для заказа еды, работающая в нескольких городах России.\
> Через сервис можно заказать любые блюда азиатской, русской, европейской кухонь из предложенных в вашем городе ресторанов и кафе.

# <a name="Описание">Описание</a>
Тестовый проект состоит из WEB-тестов, API-тестов и тестов для мобильного устройства на базе (Android).\
Краткий список интересных фактов о проекте:
- [x] В проекте применён шаблон проектирования `Page Object`
- [x] В проекте используются параметризованные тесты
- [x] Проект сконфигурирован с использованием библиотеки `Owner`
- [x] Использован `Lombok` для описания моделей API запросов/ответов
- [x] Добавлен кастомный `Allure listener` для визуализации запросов/ответов API в отчёте
- [x] Добавлена интеграция с `Allure TestOps`
- [x] Добавлена интеграция с `Jira`
- [x] Тесты запускаются в `Jenkins` с выбранными параметрами 

# <a name="Технологии и инструменты">Используемые технологии и инструменты</a>

<p  align="center">
  <code><img width="5%" title="IntelliJ IDEA" src="./images/icons/IDEA-logo.svg"></code>
  <code><img width="5%" title="Java" src="./images/icons/java-logo.svg"></code>
  <code><img width="5%" title="Selenide" src="./images/icons/selenide-logo.svg"></code>
  <code><img width="5%" title="REST-Assured" src="./images/icons/rest-assured-logo.svg"></code>
  <code><img width="5%" title="Selenoid" src="./images/icons/selenoid-logo.svg"></code>
  <code><img width="5%" title="Gradle" src="./images/icons/gradle-logo.svg"></code>
  <code><img width="5%" title="JUnit5" src="./images/icons/junit5-logo.svg"></code>
  <code><img width="5%" title="Allure Report" src="./images/icons/allure-Report-logo.svg"></code><br>
  <code><img width="5%" title="Allure TestOps" src="./images/icons/allure-ee-logo.svg"></code>
  <code><img width="5%" title="Github" src="./images/icons/git-logo.svg"></code>
  <code><img width="5%" title="Jenkins" src="./images/icons/jenkins-logo.svg"></code>
  <code><img width="5%" title="Jira" src="./images/icons/jira-logo.svg"></code>
  <code><img width="5%" title="Telegram" src="./images/icons/Telegram.svg"></code>
  <code><img width="5%" title="Browserstack" src="./images/icons/browserstack.svg"></code>
  <code><img width="5%" title="Android Studio" src="https://upload.wikimedia.org/wikipedia/commons/9/95/Android_Studio_Icon_3.6.svg"></code>
  <code><img width="5%" title="Appium" src="./images/icons/appium.svg"></code>
</p>
<br>

Автотесты в проекте написаны на `Java`.

`Gradle` - используется, как инструмент для сборки проекта.  \
`JUnit5` - используется для запуска тестов.\
`Selenide` - используется для автоматизации UI WEB.\
`REST Assured` - используется для тестирования API сервисов REST.\
`Jenkins` - используется для удаленного запуска тестов.\
`Selenoid` - используется для удаленного запуска браузера в `Docker` контейнере.\
`Browserstack` - используется для удаленного запуска мобильных тестов.\
`Android Studio tools`, `Appium Server` - используются для локального запуска мобильных тестов.\
`Allure Report` - используется для визуализации отчета о тестировании.\
`Telegram Bot` - используется для уведомления о результатах прогона тестов.\
`Allure TestOps` - используется, как репозиторий для хранения тестов.


# <a name="Как запустить">Как запустить тесты</a>

## <a name="GradleCommand">Gradle команды</a>
Для запуска локально и в Jenkins используется следующая команда:
```bash
gradle clean ${CATEGORY} -Denv=<env>
```
В `CATEGORY` представлен список из тэгов для запуска определенного типа тестов:
>- *api*
>- *web*
>- *android*

Дополнительно добавлена возможность запуска всех тестов локально и удаленно через задачи
>- *allRemoteTests*
>- *allLocalTests*

`env` - определяет среду для запуска тестов _(не относится к API)_:
>- *local*
>- *remote*

## <a name="Конфигурационные файлы">Конфигурационные файлы</a>
Возможные конфигурации окружения для тестов в проекте описаны в файлах `*.properties`\
Значения и параметры в файлах зависят от локального или удаленного запуска тестов\
Ниже представлен пример параметров, которые используются для WEB тестов
```properties
browser_name=...
browser_version=...
browser_size=...
selenoid_url=...
```

>- *browser_name* - Имя запускаемого браузера
>- *browser_version* - Версия запускаемого браузера
>- *browser_size* - Разрешение в котором будет запущен браузер
>- *selenoid_url* - Ссылка для удалённого/локального запуска тестов в `Selenoid`

## <a name="Запуск тестов в IDEA">Запуск тестов в IDEA</a>
Для удобства запуска тестов в различных конфигурациях в проекте созданы конфигурации для IDEA
<p  align="center">
<img src="images/screens/IDEARunConfigurations.png" alt="IDEARunConfigurations" width="550">
</p>