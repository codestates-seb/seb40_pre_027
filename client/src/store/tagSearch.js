import { createSlice } from '@reduxjs/toolkit';

const initialSearchState = {
  tagSearch: '',
};

const tagSearchSlice = createSlice({
  name: 'tagSearch',
  initialState: initialSearchState,
  reducers: {
    searchPost(state, action) {
      state.tagSearch = action.payload;
    },
  },
});

export const tagSearchActions = tagSearchSlice.actions;
export default tagSearchSlice;
