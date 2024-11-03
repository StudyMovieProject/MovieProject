# MovieProject

<pre>

1. Indent 규칙
  * 공백 (space bar) 4칸
  * 중괄호는 같은 줄에 열고, 코드 블록이 끝나는 부분에 닫기

EX)
  if ( [조건] ) {
  (4칸)// body
  }

2. 클래스 규칙
  * 파스칼 케이스 적용

  파스칼 케이스 (Pascal Case)
  - 클래스명은 대문자로 시작하고 각 단어의 첫글자도 대문자로 표기
  - 인터페이스 이름은 형용사 형태로 짓기 가능

  Ex) 
    UserAccount

3. 네이밍 규칙
  * 카멜케이스 적용 (변수이름, 메서드 ... )
  
  카멜 케이스 (Camel Case)
  - 맨 앞 단어의 첫 철자를 소문자로 시작하되, 그 다음 이어지는 단어의 첫 철자를 대문자로 표기하는 방식

  Ex) 
    autoHandle

4. 상수 규칙
  * 상수의 경우 모두 대문자로 설정
  * 매직넘버 사용 금지 ( 숫자나, 문자열과 같은 리터럴 값을 바로 사용하는 것 피하기, 의미있는 이름을 가진 상수로 대체 )
  
  EX)
    public static final double PI = 3.14;

5. 문장 길이  
  * 한 줄의 최대 길이는 100자 이내로 작성 (넘을경우, 적절한 위치에서 줄바꿈)
  EX)
    @GetMapping("/test")
    public String func1 ( 
          @RequestParam("test1") String test,
          @RequestParam("test2") String test2,
          @RequestParam("test3") String test3) {

         //body
         return "[테스트페이지]";
    }

</pre>
