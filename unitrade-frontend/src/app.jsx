import { BrowserRouter, Routes, Route, useParams } from "react-router-dom";

import { HomePage } from "./pages/HomePage.jsx";
import { NotFoundPage } from "./pages/NotFoundPage";
import { LoginPage } from "./pages/LoginPage";
import { SignupPage } from "./pages/SignupPage";
import { NavMenu } from './components/NavMenu';
import { CreateUniversity } from './components/CreateUniversity';
import { CreateCourse } from './components/CreateCourse';
import { ItemPage } from './pages/ItemPage';
import { Footer } from './components/Footer';
import { BrowseItemPostingPage } from "./pages/BrowseItemPostingPage.jsx";

function App() {

  const { itemId } = useParams();

  return (
    <>
      <NavMenu />
      <BrowserRouter>
        <Routes>
          <Route index element={<HomePage />} />
          <Route path="/login"  element={<LoginPage />} />
          <Route path="/signup"  element={<SignupPage />} />
          <Route path="/create-university" element={<CreateUniversity />} />
          <Route path="/create-course" element={<CreateCourse />} />
          <Route path="/browse/item" element={<BrowseItemPostingPage />} />
          <Route path="/item/:itemId" element={<ItemPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </BrowserRouter>
      {/* <Footer /> */}
    </>
  )
}

export default App
