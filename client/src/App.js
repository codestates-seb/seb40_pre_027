import './App.css';
import { lazy, Suspense } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';

//redux를 위한 import
import { useSelector } from 'react-redux';

const HomePage = lazy(() => import('./pages/HomePage'));
const LoginPage = lazy(() => import('./pages/LoginPage'));
const SigninPage = lazy(() => import('./pages/SigninPage'));
const WritePage = lazy(() => import('./pages/WritePage'));
const PostPage = lazy(() => import('./pages/PostPage'));

const EditProfilePage = lazy(() => import('./pages/EditProfilePage'));
const ProfilePage = lazy(() => import('./pages/ProfilePage'));
const Profilewrite = lazy(() =>
  import('../src/components/profile/Profilewrite')
);

function App() {
  const isLogin = useSelector((state) => state.isLogin);
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
          element={<Suspense fallback={<>...</>}>{isLogin ? <WritePage /> : <LoginPage />}</Suspense>}
        ></Route>

        <Route
          path="/post/:id"
          element={<Suspense fallback={<>...</>}>{<PostPage />}</Suspense>}
        ></Route>

        <Route
          path="/profile/edit"
          element={
            <Suspense fallback={<>...</>}>{isLogin ? <EditProfilePage /> : <LoginPage />}</Suspense>
          }
        ></Route>
        <Route
          path="/profile"
          element={
            <Suspense fallback={<>...</>}>
              {isLogin ? <ProfilePage /> : <LoginPage />}
            </Suspense>
          }
        ></Route>

        <Route
          path="/Profile/write"
          element={<Suspense fallback={<>...</>}>{isLogin ? <Profilewrite /> : <LoginPage />}</Suspense>}
        ></Route>
        <Route path="*" element={<Navigate to="/" />}></Route>
      </Routes>
    </div>
  );
}

export default App;
