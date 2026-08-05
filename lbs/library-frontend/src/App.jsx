import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

import Layout from "./components/Layout";
import PrivateRoute from "./utils/PrivateRoute";

import Dashboard from "./pages/Dashboard";
import Books from "./pages/Books";
import Students from "./pages/Students";
import Authors from "./pages/Authors";
import IssueBook from "./pages/IssueBook";
import ReturnBook from "./pages/ReturnBook";
import Transactions from "./pages/Transactions";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Payment from "./pages/Payment";
import PaymentHistory from "./pages/PaymentHistory";
import StudentDashboard from "./pages/StudentDashboard";
import StudentProfile from "./pages/StudentProfile";

function App() {

  return (

    <BrowserRouter>


      <Routes>
                  <Route
                      path="/login"
                      element={<Login />}
                  />

                  <Route
                      path="/signup"
                      element={<Signup />}
                  />

       <Route
         path="/"
         element={
           <PrivateRoute>
             <Layout>
               <Dashboard />
             </Layout>
           </PrivateRoute>
         }
       />

        <Route
          path="/books"
          element={
            <PrivateRoute>
              <Layout>
                <Books />
              </Layout>
            </PrivateRoute>
          }
        />
            <Route
                path="/student-dashboard"
                element={
                    <PrivateRoute>
                        <Layout>
                            <StudentDashboard />
                        </Layout>
                    </PrivateRoute>
                }
            />

            <Route
                path="/student-profile"
                element={
                    <PrivateRoute>
                        <Layout>
                            <StudentProfile />
                        </Layout>
                    </PrivateRoute>
                }
            />

        <Route
          path="/students"
          element={
            <PrivateRoute>
              <Layout>
                <Students />
              </Layout>
            </PrivateRoute>
          }
        />

        <Route
          path="/authors"
          element={
            <PrivateRoute>
              <Layout>
                <Authors />
              </Layout>
            </PrivateRoute>
          }
        />

        <Route
          path="/issue-book"
          element={
            <PrivateRoute>
              <Layout>
                <IssueBook />
              </Layout>
            </PrivateRoute>
          }
        />

        <Route
          path="/return-book"
          element={
            <PrivateRoute>
              <Layout>
                <ReturnBook />
              </Layout>
            </PrivateRoute>
          }
        />

        <Route
          path="/transactions"
          element={
            <PrivateRoute>
              <Layout>
                <Transactions />
              </Layout>
            </PrivateRoute>
          }
        />

        <Route path="/payment" element={<Payment />} />

        <Route path="/payment-history" element={<PaymentHistory />} />

      </Routes>

    </BrowserRouter>

  );

}

export default App;