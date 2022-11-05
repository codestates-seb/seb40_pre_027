import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

  /* 
  1. 깃헙 소셜 로그인 버튼 누르면 oauthHandler 함수가 작동함 
  2. oauthHandler 함수 내의 window.location.assign 을 통해 깃헙 소셜 로그인 창으로 이동.
  3. CLIENT_ID는 사전에 깃헙 oauth 페이지에서 발급 받음
  4. 깃헙 소셜로그인 창에서 로그인을 완료하고 권한 부여버튼을 누르면 CLIENT_ID를 발급받은 깃헙 oauth페이지 설정에 등록된 callback 주소로 이동
  그리고 authorizationCode가 발급됨
  5. callback 주소 페이지로 이동하면 해당 페이지의 useEffect를 통해 authorizationCode를 서버로 전달해야함.
  6. authorizationCode는 주소창에서 확인가능 
  (window.location.href를 통해 주소를 따오고, 해당 주소를 url변수에 저장하여, url.searchParams.get("code")를 통해 authorizationCode를 authorizationCode 변수에 저장)
  7. getAccessToken 함수를 만들어 위 useEffect에서 호출.
  8. getAccessToken 함수는 axios post요청으로 authorizationCode를 서버에 전달하고, 응답으로 서버에서 accessToken을 받으면서 isLogin 상태를 true로 변경
  9. accessToken은 상태로 저장하여,
   만약 로그인된 깃헙 유저의 정보를 가져오고 싶다면, 서버의 알맞은 endpoint로 accessToken을 첨부하여 axios 요청을 보내는 것으로 정보를 가져올 수 있다.
   10. 로그아웃 또한 accessToken을 axios 요청으로 서버에 delete 메소드로 보내면된다. logoutHandler 함수 참고.
  */

function App() {
  const [accessToken, setAccessToken] = useState("");
  const [isLogin, setIsLogin] = useState(false);

  //github oauth에서 발급받은 CLIENT_ID
  const CLIENT_ID = "75bf63ebfe804e493500";

  //소셜 로그인창으로 연결
  const oauthHandler = () => {
    return window.location.assign(
      `https://github.com/login/oauth/authorize?client_id=${CLIENT_ID}`
    );
  };

  //accessToken을 백엔드 서버에 요청
  const getAccessToken = async (authorizationCode) => {
    await axios
      .post("https://localhost:4000/callback", { authorizationCode })
      .then((res) => {
        const { data } = res;
        //console.log(data)
        setAccessToken(data.accessToken);
        setIsLogin(true);
      });
  };

  //authorizationCode를 변수에 저장
  useEffect(() => {
    const url = new URL(window.location.href);
    const authorizationCode = url.searchParams.get("code");
    if (authorizationCode) {
      console.log(authorizationCode);
      getAccessToken(authorizationCode);
    }
  }, []);

  //백엔드 서버에 로그아웃 요청
  const logoutHandler = () => {
    axios({
      method: "delete",
      url: "https://localhost:4000/logout",
      data: { accessToken },
    })
      .then((el) => setIsLogin(false))
      .catch((err) => console.log(err.response.data));
  };

  return (
    <div className="App">
      <button onClick={oauthHandler}>github auth</button>
      {isLogin && (
        <div accessToken={accessToken}>
          로그인 완료
          <button onClick={logoutHandler}>Log out</button>
        </div>
      )}
    </div>
  );
}

export default App;
