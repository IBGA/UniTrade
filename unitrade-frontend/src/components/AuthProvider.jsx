import { createContext, useContext, useEffect, useState } from 'react';
import { GET } from "../utils/client";

const AuthContext = createContext({
  auth: false,
  setAuth: () => {},
  user: null
});

const useAuth = () => useContext(AuthContext);

const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(false);
  const [user, setUser] = useState(null);

  useEffect(() => {
    const isAuth = async () => {
      try {
        const res = await GET('authenticated', true);
        setAuth(!res.error);
        if (auth) {
          const userRes = await GET('person/self', true);
          setUser(userRes)
        } else {
          setUser(null);
        }
      } catch(error) {
        setAuth(false);
        setUser(null);
      };
    };

    isAuth();
  }, [auth]);

  return (
    <AuthContext.Provider value={{ auth, setAuth, user }}>
      {children}
    </AuthContext.Provider>
  );
};

export { AuthProvider, useAuth };