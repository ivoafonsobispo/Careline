import { useRouteError } from "react-router-dom";

export default function ErrorPage() {

  return (
    <div id="error-page">
      <h1>Oops!</h1>
      <p>Sorry, the page you're looking for does not exist.</p>
    </div>
  );
}