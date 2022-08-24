## `Thunisoft-ui`（UI组件库）

### `Changelog`：
- `1.0.0`
    - 增加通用color
    - 适配androidX
    - 更新common版本到1.0.4
    - 修改自定义Dialog样式
    - 修改自定义ProgressDialog实现方式
    - 增加基于AppBarLayout实现自定义TitleLayout
    - 删除sp转px时需要传入的参数context
    - 增加dimens字体值定义12sp、14sp、16sp、18sp
    - 修改自定义Dialog样式，与系统Material AlertDialog风格一致
    - 修正编译错误
    - 增加ProgressDialog自定义样式
    - 增加自定义BottomNavigationBar
    - 增加部分自定义View
    - 增加通用自定义dialog样式与设置Primary颜色
    - 增加progress dialog

## 如何使用？

- 在项目`build.gradle`文件中加入如下代码：

  ```groovy
  allprojects {
      repositories {
          maven { 
              url "http://repo.thunisoft.com/maven2/content/repositories/releases/" 
          }
      }
  }
  ```



- 在需要引用的module的`build.gradle`文件中加入如下代码：

    ```groovy
    // ui
    implementation 'com.thunisoft.android:ui:版本号
    ```
    
- `ui`库需要在Application中初始化

  ```java
  ThunisoftUI.init()
  ```