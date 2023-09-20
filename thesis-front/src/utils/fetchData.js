export default async function fetchData(path) {
    try {
      const response = await fetch(path);
      console.log(response)
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
  
      const responseData = await response.json();
      return responseData;
    } catch (error) {
      throw error;
    }
  }