import axios from 'axios';
axios.defaults.withCredentials = true;

async function requestDataWithToken(setFunc, url, method, data) {
  let accessToken = localStorage.getItem('accessToken');
  const refreshToken = localStorage.getItem('refreshToken');
  const api = process.env.REACT_APP_API_URL;

  try {
    const res = await axios({
      method,
      url,
      data,
      headers: { access: accessToken },
    });
    console.log('첫요청 성공', res);
    if (setFunc) setFunc(res.data);
  } catch (err) {
    if (err.response.status === 401) {
      try {
        const resForToken = await axios.get(`${api}/user/auth/reissue`, {
          headers: { access: accessToken, refresh: refreshToken },
        });
        localStorage.setItem('accessToken', resForToken.headers.access);
        accessToken = localStorage.getItem('accessToken');
        const reRes = await axios({
          method,
          url,
          data,
          headers: { access: accessToken },
        });
        console.log('재요청 성공', reRes);
        if (setFunc) setFunc(reRes.data);
      } catch (err) {
        console.log('재요청 실패', err);
      }
    }
    console.log('첫요청 실패', err);
  }
}

export default requestDataWithToken;
