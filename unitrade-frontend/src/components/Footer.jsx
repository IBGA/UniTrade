import React from 'react';
import styled from 'styled-components';

const FooterStyle = styled.div`
  .footer-container {
    position: absolute;
    bottom: 0;
    width: 100%;
    font-size: 1rem;
    border-top: 0.3rem solid var(--grey);
    text-align: left;
    padding: 1rem 0;
    margin-top: 5rem;
  }
  .footer-container > span {
    margin-left: 1rem;
  }
`;

export function Footer() {
  return (
    <FooterStyle>
      <div className="footer-container">
        <span>© 2023 - IBGA</span>
      </div>
    </FooterStyle>
  );
}