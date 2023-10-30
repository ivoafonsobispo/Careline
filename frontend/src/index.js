import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import ClientHome from './Routes/ClientHome';
import ClientDiagnoses from './Routes/ClientDiagnoses'
import ErrorPage from './Routes/ErrorPage';

import { createBrowserRouter, RouterProvider } from 'react-router-dom';

const root = ReactDOM.createRoot(document.getElementById('root'));

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      { 
        index: true,
        element: <ClientHome /> 
      },
      {
        path: '/diagnoses',
        element: <ClientDiagnoses />,
        errorElement: <ErrorPage />
      },
    ],
  },
  
]);

root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
