import { BrowserRouter, Routes, Route } from "react-router-dom";

import { HomePage } from "./pages/HomePage.jsx";
import { NotFoundPage } from "./pages/NotFoundPage";
import { LoginPage } from "./pages/LoginPage";
import { SignupPage } from "./pages/SignupPage";
import { NavMenu } from './components/NavMenu';
import { CreateUniversityPage } from './pages/CreateUniversityPage.jsx';
import { CreateCourse } from './components/CreateCourse';
import { Footer } from './components/Footer';
import { BrowseItemPostingPage } from "./pages/BrowseItemPostingPage.jsx";

function App() {
  return (
    <>
      <NavMenu />
      <BrowserRouter>
        <Routes>
          <Route index element={<HomePage />} />
          <Route path="/login"  element={<LoginPage />} />
          <Route path="/signup"  element={<SignupPage />} />
          <Route path="/create-university" element={<CreateUniversityPage />} />
          <Route path="/create-course" element={<CreateCourse />} />
          <Route path="/browse/post/item" element={<BrowseItemPostingPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </BrowserRouter>
      {/* <Footer /> */}
    </>
  )
}

export default App
