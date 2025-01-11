# desktoppet_ultimate

基于JavaFX制作的桌面宠物程序

## 项目结构

- `src/main/java/local/desktoppet/` - Java 源代码
- `src/main/resources/local/desktoppet/` - 资源文件（如 FXML、CSS、图像等）
- `target/` - 编译后的类文件

## 安装

1. 克隆仓库：
    ```sh
    git clone https://github.com/ZUTA39/desktoppet_ultimate.git
    ```
2. 导入项目到你的 IDE（如 IntelliJ IDEA 或 Eclipse）。
3. 确保你已经安装了 Java 11、Maven和JDBC驱动。
4. 确保你已经安装了MySQL并创建了一个数据库（pet_account_record）

## 构建和运行

1. 将所有类中的MySQL用户名与密码改成你自己的
2. 在项目根目录下运行以下命令来构建和运行项目：
  ```sh
  mvn clean javafx:run
  ```

## 功能介绍

1. 登录与注册
2. 桌面宠物本体（可响应点击、拖动、投喂、文件拖入）
3. 音乐播放
4. 记账（增删改查、数据统计）
5. 完善的安全性检查
