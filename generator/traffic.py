import asyncio
import aiohttp
import time

TEST_URL = 'http://localhost:8080/test'
TIMEOUT = 5     # second

async def get_coroutine(session: aiohttp.ClientSession):
    async with session.get(TEST_URL) as response:
        if response.status == 200:
            return True, response.status, None
        else:
            return False, response.status, response.reason if hasattr(response, 'reason') else None

async def send_requests(result_list: list):
    async with aiohttp.ClientSession() as session:
        try:
            success, status, reason = await asyncio.wait_for(get_coroutine(session), timeout=TIMEOUT)
        except asyncio.TimeoutError:
            result_list.append((False, 0, 'Request timeout'))
        except asyncio.CancelledError:
            raise
        # except asyncio.Can
        except Exception as e:
            result_list.append((False, 0, f'Request failed: {str(e)}'))
        else:
            if success:
                result_list.append((success, status, None))
            else:
                result_list.append((success, status, reason))
        await asyncio.sleep(1)

async def main():
    for n_requests in range(1000, 11000, 1000):
        result_list = []
        tasks = []
        for i in range(n_requests):
            task = asyncio.create_task(send_requests(result_list))
            tasks.append(task)
        
        await asyncio.gather(*tasks, return_exceptions=True)

        success_count = sum(1 for success, _, _ in result_list if success)
        total_count = len(result_list)
        success_rate = success_count / total_count * 100
        print(f'응답 성공률 => {n_requests} requests/s: {success_rate:.2f}% ({success_count}/{total_count})')
        time.sleep(6)

if __name__ == "__main__":
    asyncio.run(main())