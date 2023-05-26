import { configureStore } from '@reduxjs/toolkit';
import storage from 'redux-persist/lib/storage';
import { combineReducers } from 'redux';
import { persistReducer } from 'redux-persist';
import { createSlice } from '@reduxjs/toolkit';
import thunk from 'redux-thunk';

const loginId = createSlice({
    name: "loginId",
    initialState: { value: -1 },
    reducers: {
        set: ( state, action )=> {
            state.value = action.id
        }
    }
})
const users = createSlice({
    name: "users",
    initialState: { value: [] },
    reducers: {
        set: ( state, action )=> {
            state.value = action.users
        }
    }
})
const admins = createSlice({
    name: "admins",
    initialState: { value: [] },
    reducers: {
        set: ( state, action )=> {
            state.value = action.admins
        }
    }
})
const products = createSlice({
    name: "products",
    initialState: { value: [] },
    reducers: {
        set: ( state, action )=> {
            state.value = action.products
        }
    }
})

const reducers = combineReducers ({
    loginId: loginId.reducer,
    users: users.reducer,
    admins: admins.reducer,
    products: products.reducer
})
const persistConfig = {
    key: "root",
    storage
}
const persistedReducer = persistReducer(persistConfig, reducers)

const store = configureStore({
    reducer: persistedReducer,
    middleware: [thunk]
})

export default store;