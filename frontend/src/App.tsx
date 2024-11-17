import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { Home } from "./components/Home";
import { Success } from "./components/Success";
import { Error } from "./components/Error";


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/oauth2/success" element={<Success />} />
        <Route path="/oauth2/error" element={<Error />} />
      </Routes>
    </Router>
  );
}

export default App;
