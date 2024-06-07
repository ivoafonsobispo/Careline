import { configureStore } from '@reduxjs/toolkit'
import tokenReducer from './TokenSlice'

export default configureStore({
  reducer: {
    auth: tokenReducer,
  },

})