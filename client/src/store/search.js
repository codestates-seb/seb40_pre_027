import { createSlice } from '@reduxjs/toolkit';

const initialSearchState = {
  searchInput: '',
};

const searchSlice = createSlice({
  name: 'search',
  initialState: initialSearchState,
  reducers: {
    searchPost(state, action) {
      state.searchInput = action.payload;
    },
  },
});

export const searchActions = searchSlice.actions;
export default searchSlice;
