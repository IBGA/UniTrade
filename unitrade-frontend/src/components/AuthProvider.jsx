import { createContext, useContext, useEffect, useState } from 'react';
import { GET } from "../utils/client";

const AuthContext = createContext({
  auth: false,
  setAuth: () => {},
});

const useAuth = () => useContext(AuthContext);

const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(false);

  useEffect(() => {
    const isAuth = async () => {
      try {
        const res = await GET('authenticated', true);
        setAuth(!res.error);
      } catch(error) {
        setAuth(false);
      };
    };

    isAuth();
  }, [auth]);

  return (
    <AuthContext.Provider value={{ auth, setAuth }}>
      {children}
    </AuthContext.Provider>
  );
};

export { AuthProvider, useAuth };