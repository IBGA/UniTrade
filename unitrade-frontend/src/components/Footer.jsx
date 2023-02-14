import React from 'react';
import styled from 'styled-components';

const FooterStyle = styled.div`
  .copyright {
    font-size: 1rem;
    border-top: 0.3rem solid var(--grey);
    text-align: left;
    padding: 1rem 0;
    margin-top: 5rem;
  }
`;

export function Footer() {
  return (
    <FooterStyle>
      <div className="copyright">
        <div className="container">
            © 2023 - IBGA
        </div>
      </div>
    </FooterStyle>
  );
}