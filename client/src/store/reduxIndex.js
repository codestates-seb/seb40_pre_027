import { configureStore } from '@reduxjs/toolkit';
import loginSlice from './login';
import searchSlice from './search';
import tagSearch from './tagSearch';

const store = configureStore({
  reducer: {
    login: loginSlice.reducer,
    search: searchSlice.reducer,
    tagSearch: tagSearch.reducer,
  },
});

export default store;
