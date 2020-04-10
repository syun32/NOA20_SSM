# NOA20_ICT
NOA 20 | 스마트 조미료통과 연동할 어플리케이션

## 설명
사용자가 어플리케이션을 통해 레시피를 입력하면 레시피에 사용되는 조미료를 서버에 있는 DB에 저장한다.   
레시피를 실행하면 서버에 있는 데이터를 조미료통으로 전송한다.   
데이터를 받게 되면 조미료통에서 조미료가 분출된다.   

## 기능
* 사용자가 자주 사용하는 레시피 저장 및 관리
* 레시피에 저장하지 않고 '바로 실행' 가능
* 조미료통과 통신
* PHP를 통해 MySQL과 연동하여 사용자 데이터베이스 생성
* 사용자 데이터 시각화

## [Android](https://github.com/eun-seong/NOA20_SSM/tree/master/NOA_ICT)
안드로이드 어플리케이션    
#### InitialActivity.java 
사용자가 조미료통에 어떤 조미료를 넣을지 설정하는 액티비티   
주요 코드   
```java
PreferenceManager.setString(mContext, "condiment0", condiment_[0]);
PreferenceManager.setString(mContext, "condiment1", condiment_[1]);
```
   
#### MainActivity.java
사용자가 설정한 레시피를 서버에서 불러와 리스트뷰를 통해 보여주는 액티비티      
레시피를 실행하면 조미료통으로 값을 전달한다.    
주요 코드
```java
String myJSON = new GetData().execute("getData.php").get();

JSONObject jsonObject = new JSONObject(myJSON);
JSONArray dataJSON = jsonObject.getJSONArray(getString(R.string.DB_tableName));

int num = dataJSON.length();
for (int I = 0; I < num; I++) {
    Log.d(TAG, "updateLV: for loop number : " + i);
    JSONObject c = dataJSON.getJSONObject(i);
    int id = c.getInt(getString(R.string.DB_colID));
    int[] gram = {Integer.parseInt(c.getString(getString(R.string.DB_colCondiment0))), Integer.parseInt(c.getString(getString(R.string.DB_colCondiment1)))};
    Data data = new Data();

    data.setId(id);
    data.setTitle(c.getString(getString(R.string.DB_colTitle)));
    data.setCondiment0(condiment[0]);
    data.setCondiment1(condiment[1]);
    data.setGram0(gram[0]);
    data.setGram1(gram[1]);

    list.add(data);
}
```

#### PlayActivity.java
레시피에 저장하지 않고 실시간으로 실행하는 액티비티    
주요 코드   
```java
String gram0 = gram[0].getText().toString();
String gram1 = gram[1].getText().toString();

InsertData task = new InsertData();
task.execute("executeData.php",
        getString(R.string.DB_colCondiment0), gram0,
        getString(R.string.DB_colCondiment1), gram1
);
```


## Server
### MySQL
서버에 존재하는 데이터베이스
#### TABLE | usr_statistics
사용자가 저장한 모든 레시피가 저장되어 있다.   
각 열은 레시피의 아이디, 레시피 이름, 조미료1, 조미료2를 의미한다.
```sql
mysql> desc recipe;
+------------+----------+------+-----+---------+----------------+
| Field      | Type     | Null | Key | Default | Extra          |
+------------+----------+------+-----+---------+----------------+
| id         | int      | NO   | PRI | NULL    | auto_increment |
| title      | char(30) | YES  |     | NULL    |                |
| condiment0 | int      | YES  |     | NULL    |                |
| condiment1 | int      | YES  |     | NULL    |                |
+------------+----------+------+-----+---------+----------------+
```

#### TABLE | ino_data
아두이노로 보낼 데이터가 담겨 있다.    
각 열은 조미료통의 아이디, 실행할 조미료의 그램수를 의미한다.     
2개의 행이 있고,  id는 각각 0,1이다.  condiment는 항상 0이고 사용자가 앱을 통해 실행하면 사용자가 입력한 값으로 업데이트되어 아두이노가 수신한 후 다시 0으로 변경된다.     
```sql
mysql> desc ino;
+-----------+------+------+-----+---------+-------+
| Field     | Type | Null | Key | Default | Extra |
+-----------+------+------+-----+---------+-------+
| id        | int  | YES  |     | NULL    |       |
| condiment | int  | YES  |     | NULL    |       |
+-----------+------+------+-----+---------+-------+
```

### [PHP](https://github.com/eun-seong/NOA20_SSM/tree/master/PHP)
서버에서 실행되는 php 파일
#### inoExecuteData.php
오류를 줄이기 위해 데이터를 '*'를 붙여 전송한다.       
아두이노는 '*'가 붙은 데이터만 수신한다.    
주요 코드
```php
$data = array();
$row = $stmt->fetch(PDO::FETCH_ASSOC);
extract($row);

try {
    $stmt = $con->prepare("UPDATE ino SET condiment=0 WHERE id={$id}");
} catch (PDOException $e) {
    die("Database error: " . $e->getMessage());
}

echo "*" . $row['condiment'];
```

