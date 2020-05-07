/* API call for GET method. */
const apiFetch = async (url) => {
  const response = await fetch(url);
  const body = await response.json();
  if (response.status !== 200) throw Error(body.message);
  return body;
};

/* API call for POST method. */
const apiPost = async (url, body) => {
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  });
  const responseBody = await response.json();
  console.log(body);
  if (response.status !== 200) throw Error(responseBody.message);
  return responseBody;
}

/* API call for PUT method. */
const apiPut = async (url, body) => {
  const response = await fetch(url, {
    method: 'PUT',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  });
  const responseBody = await response.json();
  if (response.status !== 200) throw Error(responseBody.message);

  return responseBody;
}

/* API call for DELETE method. */
const apiDelete = async (url, body) => {
  const response = await fetch(url, {
    method: 'DELETE',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body)
  });
  const responseBody = await response.json();
  if (response.status !== 200) throw Error(responseBody.message);
  return responseBody;
}

const convertToBase64 = file => new Promise((resolve, reject) => {
  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = () => resolve(reader.result);
  reader.onerror = error => reject(error);
});

/* Convert file into base 64 string .*/
const fileToBase64 = async (file) => {
 const result =   await convertToBase64(file);
 return  result.split(",")[1];
}

export {apiFetch, apiPost,apiPut,apiDelete, fileToBase64};
 