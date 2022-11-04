import axios from 'axios';

async function requestDataWithToken(setFunc) {
  let accessToken = localStorage.getItem('accessToken');
  const refreshToken = localStorage.getItem('refreshToken');
  try {
    const res = await axios.get(`/user/profile`, {
      headers: { access: accessToken },
    });
    console.log('첫요청 성공', res);
    if (setFunc) setFunc(res.data);
  } catch (err) {
    if (err.response.status === 401) {
      try {
        const resForToken = await axios.get('/user/auth/reissue', {
          headers: { access: accessToken, refresh: refreshToken },
        });
        localStorage.setItem('accessToken', resForToken.headers.access);
        accessToken = localStorage.getItem('accessToken');
        const reRes = await axios.get(`/user/profile`, {
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
