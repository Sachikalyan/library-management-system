@echo off
echo ========================================================
echo   Uploading Library Management System to GitHub
echo ========================================================
echo.

git status >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Error: Git is not installed or this is not a git repo.
    pause
    exit /b
)

echo Pushing main branch to GitHub...
git push -u origin main

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================================
    echo   SUCCESS! Uploaded to GitHub successfully!
    echo ========================================================
) else (
    echo.
    echo ========================================================
    echo   NOTE: If it failed, please make sure you created the
    echo   empty repository 'library-management-system' on GitHub
    echo   at https://github.com/new first!
    echo ========================================================
)

echo.
pause
