import { createSlice } from '@reduxjs/toolkit'

export const tokenSlice = createSlice({
  name: 'auth',
  initialState: {
    token: null,
    user: null,
  },
  reducers: {
    tokenSetter: (state, action) => {
      state.token = action.payload
    },
    userSetter: (state, action) => {
      state.user = action.payload
    },
  },
})

// Action creators are generated for each case reducer function
export const { tokenSetter, userSetter } = tokenSlice.actions

export default tokenSlice.reducer