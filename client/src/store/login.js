import { createSlice } from '@reduxjs/toolkit';

const initialToken = localStorage.getItem('accessToken');
const initialLoginState = {
  isLogin: initialToken ? true : false,
  accessToken: initialToken,
};

const loginSlice = createSlice({
  name: 'isLogin',
  initialState: initialLoginState,
  reducers: {
    login(state) {
      state.isLogin = true;
    },
    logout(state) {
      state.isLogin = false;
    },
  },
});

export const loginActions = loginSlice.actions;
export default loginSlice;
