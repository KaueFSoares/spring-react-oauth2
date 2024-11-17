import { useSearchParams } from "react-router-dom";

export const Error = () => {
  const [searchParams] = useSearchParams();

  const message = searchParams.get("message");

  return (
    <div>
      <h2>Failure</h2>

      {message ? (
        <div>
          <p><strong>Message: </strong> {message} </p>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  )
}
