import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { publicRoutes } from "./routes/Routes.index";
import { DefaultLayout, MessageLayout, ProfileLayout } from "./components/Layouts/Layout.index";
import { Fragment } from "react";
import 'stories-react/dist/index.css';
import 'react-date-picker/dist/DatePicker.css';
import 'react-calendar/dist/Calendar.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          {publicRoutes.map((route, index) => {
            const Page = route.component;
            let Layout = null
            if (route.layout === null){
              Layout = DefaultLayout;
            }else if (route.layout === ""){
              Layout = Fragment;
            }else if(route.layout === "Profile"){
              Layout = ProfileLayout;
            }else if(route.layout === "Chat"){
              Layout = MessageLayout;
            }
            return (
              <Route
                key={index}
                path={route.path}
                element={
                  <Layout>
                    <Page />
                  </Layout>
                }
              />
            );
          })}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
