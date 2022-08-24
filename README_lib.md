#
## 公共基础工具类库、日志 工具类、UI组件库。

#### 1. `Thunisoft-common`（基础工具库）
- `ReleaseNotes`：
  - `1.0.0`
    - 适配androidX
    - 优化网络请求异常处理
    - 增加网络请求通用框架
    - 增加bitmap工具类
    - 增加Fragment懒加载base类
    - 增加公共基础工具类库，基类

#### 2. `Thunisoft-logger`（日志工具库）
- `ReleaseNotes`：
  - `1.0.0`
    - 增加logback
    - 适配androidX
    - 统一封装logger初始化动作
    - 修正保存文件格式错误问题，修改存储目录
#### 3. `Thunisoft-ui`（UI组件库）
- `ReleaseNotes`：
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
---

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
    // common
    implementation 'com.thunisoft.android:common:版本号
    // logger
    implementation 'com.thunisoft.android:logger:版本号
    // ui
    implementation 'com.thunisoft.android:ui:版本号
    ```

- `common`与`ui`库需要在Application中初始化

  ```
  ThunisoftCommon.init()
  
  ThunisoftUI.init()
  ```
  
- `logger`库需要如下初始化logger

  ***废弃***
  ```java
  /**
     * 初始化logger
     */
    private void initLogger() {
        //log打印格式策略
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)   // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("Demo")
                .build();

        //android log 开关
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        //log存储文件格式策略
        Logger.addLogAdapter(new DiskLogAdapter(DiskFormatStrategy.newBuilder().build(this)));
    }
  ```
  ***变更为***
  ```java
      ThunisoftLogger.initLogger(this, new LoggerConfig() {
            @Override
            public String getTag() {
                return "xxxx";
            }

            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }
        });
  ```