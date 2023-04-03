import { createContext, useContext, useEffect, useState } from 'react';
import { GET } from "../utils/client";

const AuthContext = createContext({
  auth: false,
  setAuth: () => {},
  user: null,
  role: 0
});

const useAuth = () => useContext(AuthContext);

const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(false);
  const [user, setUser] = useState(null);
  const [role, setRole] = useState(0);

  useEffect(() => {
    const isAuth = async () => {
      try {
        const res = await GET('authenticated', true);
        setAuth(!res.error);
        if (auth) {
          const userRes = await GET('person/self', true);
          setUser(userRes)
          const role = await GET('role/self/role/university', true);
          setRole(role);
        } else {
          setUser(null);
          setRole(0);
        }
      } catch(error) {
        setAuth(false);
        setUser(null);
        setRole(0);
      };
    };

    isAuth();
  }, [auth]);

  return (
    <AuthContext.Provider value={{ auth, setAuth, user, role }}>
      {children}
    </AuthContext.Provider>
  );
};

export { AuthProvider, useAuth };