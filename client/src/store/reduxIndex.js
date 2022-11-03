import { configureStore } from '@reduxjs/toolkit';
import loginSlice from './login';
import searchSlice from './search';

const store = configureStore({
  reducer: { login: loginSlice.reducer, search: searchSlice.reducer },
});

export default store;
