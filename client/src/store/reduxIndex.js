import { createSlice, configureStore } from '@reduxjs/toolkit';

const initialState = { isLogin: false };

const loginStore = createSlice({
  name: 'isLogin',
  initialState,
  reducers: {
    login(state) {
      state.isLogin = true;
    },
    logout(state) {
      state.isLogin = false;
    },
  },
});

const store = configureStore({
  reducer: loginStore.reducer,
});

export const loginActions = loginStore.actions;

export default store;
