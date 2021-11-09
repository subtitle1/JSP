# Tomcat
- Web Application Server(WAS)
- Web application program을 실행시켜주는 서버이다.
- Servlet/JSP 엔진, Servlet/JSP 컨테이너의 역할을 수행하는 서버다.
  + 웹 애플리케이션 서버는 브라우저(클라이언트)가 요청을 보내면 그 요청을 분석해 웹 애플리케이션을 실행시키고,
    브라우저에게 응답 메세지를 보낸다.
  + SQL Developer은 Oracle과 세션으로 연결되어 있는데 sql의 쿼리를 분석해 resultSet을 반환한다.
  
# URL
- URL(Uniform Resource Locator)
  + 자원(웹페이지, 웹애플리케이션, 그림, 동영상, 파일)의 위치를 알려주는 주소다.
  + 브라우저(클라이언트)의 주소창에 URL을 입력하면 웹브라우저가 해당 URL을 서버로 전달한다.

## JSP 개발 환경 설정 (jdk, eclipse EE, Tomcat이 설치 완료되어 있어야 한다)
1. 워크스페이스 설정 (본인이 편한 경로로 지정한다)
![image](https://user-images.githubusercontent.com/87356533/140906681-a3448d7a-111d-4fa0-9ce4-dfe7bbfcbf21.png)

2. 이클립스의 window-preferences에서 css, html, jsp file의 encoding 설정을 utf-8로 변경한다.
![image](https://user-images.githubusercontent.com/87356533/140905884-1df9b7ba-883e-4164-bcf9-a33d23cc8948.png)

3. window-preferences에서 server-Runtime Environment에 들어가서 add를 클릭하고, Apache Tomcat v10.0 선택 후 next
![image](https://user-images.githubusercontent.com/87356533/140907397-003e6f7e-9477-46d0-a1ea-e34fd188365a.png)

4. 다음 창에서 톰캣을 설치한 경로로 지정해 주고 finish
![image](https://user-images.githubusercontent.com/87356533/140907793-e4a4fe03-1764-478d-9d33-efb9ce78f2a5.png)

5. Web-Dynamic Web Project에서 프로젝트를 생성한다.
![image](https://user-images.githubusercontent.com/87356533/140908388-25732ddb-a4c3-44d1-9857-ca14655ac81c.png)

## 프로젝트 생성 후 폴더 살펴보기
- src/main/java : 자바 패키지, 클래스, 인터페이스 정의하는 곳
- src/main/resources : 환경 설정 파일을 정의하는 곳
- src/test/java : 테스트케이스를 정의하는 곳(src/main/java에서 정의한 프로그램을 테스트하는 프로그램 정의)
- src/test/resources : 테스트케이스 실행에 필요한 환경설정파일을 정의하는 곳

- src/main/webapp : 폴더, 이미지, css파일, javaScript파일, JSP파일을 정의하는 곳
- src/main/webapp/META-INF : 외부에서 접근이 차단된 폴더(숨겨진 폴더), 웹 애플리케이션 실행에 필요한 부가적인 설정파일을 정의하는 곳
- src/main/webapp/WEB-INF : 외부에서 접근이 차단된 폴더(숨겨진 폴더), 웹 애플리케이션 실행에 필요한 설정파일을 정의하는 곳
- src/main/webapp/WEB-INF/lib : 외부에서 접근이 차단된 폴더(숨겨진 폴더), 웹 애플리케이션 실행에 필요한 jar파일이 위치한 곳
