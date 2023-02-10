import { Routes, Route } from "react-router-dom";

import { HomePage } from "./pages/HomePage.jsx";
import { NotFoundPage } from "./pages/NotFoundPage";

function Router() {

  return (
    <Routes>
      <Route index element={<HomePage />} />
      {/* <Route path="/other" element={<Other />} /> */}
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  )
}

export default Router
