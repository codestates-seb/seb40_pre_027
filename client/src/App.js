import './App.css';
import { lazy, Suspense } from 'react';
import { Routes, Route } from 'react-router-dom';

const HomePage = lazy(() => import('./pages/HomePage'));
const LoginPage = lazy(() => import('./pages/LoginPage'));
const SigninPage = lazy(() => import('./pages/SigninPage'));
const WritePage = lazy(() => import('./pages/WritePage'));
const PostPage = lazy(() => import('./pages/PostPage'));

function App() {
  return (
    <div className="App">
      <Routes>
        <Route
          path="/"
          element={<Suspense fallback={<>...</>}>{<HomePage />}</Suspense>}
        ></Route>

        <Route
          path="/questions"
          element={<Suspense fallback={<>...</>}>{<HomePage />}</Suspense>}
        ></Route>

        <Route
          path="/questions"
          element={<Suspense fallback={<>...</>}>{<HomePage />}</Suspense>}
        ></Route>

        <Route
          path="/login"
          element={
            <Suspense fallback={<>...</>}>
              <LoginPage />
            </Suspense>
          }
        ></Route>

        <Route
          path="/sign"
          element={<Suspense fallback={<>...</>}>{<SigninPage />}</Suspense>}
        ></Route>

        <Route
          path="/write"
          element={<Suspense fallback={<>...</>}>{<WritePage />}</Suspense>}
        ></Route>

        <Route
          path="/post/:id"
          element={<Suspense fallback={<>...</>}>{<PostPage />}</Suspense>}
        ></Route>
      </Routes>
    </div>
  );
}

export default App;
