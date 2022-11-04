import axios from 'axios';

async function requestDataWithToken(setFunc, url, method, data) {
  let accessToken = localStorage.getItem('accessToken');
  const refreshToken = localStorage.getItem('refreshToken');
  try {
    const res = await axios(
      { method , url , data, headers: { access: accessToken }}
    );
    console.log('첫요청 성공', res);
    setFunc(res.data);
  } catch (err) {
    if (err.response.status === 401) {
      try {
        const resForToken = await axios.get('/user/auth/reissue', {
          headers: { access: accessToken, refresh: refreshToken },
        });
        localStorage.setItem('accessToken', resForToken.headers.access);
        accessToken = localStorage.getItem('accessToken');
        const reRes = await axios(
          { method, url, data, headers: { access: accessToken } }
        );
        console.log('재요청 성공', reRes);
        setFunc(reRes.data);
      } catch (err) {
        console.log('재요청 실패', err);
      }
    }
    console.log('첫요청 실패', err);
  }
}

export default requestDataWithToken;
